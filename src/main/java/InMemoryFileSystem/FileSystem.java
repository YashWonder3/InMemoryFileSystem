package InMemoryFileSystem;

import java.util.HashMap;
import java.util.Map;

public class FileSystem {
    private final Map<String, Drive> drivesByName = new HashMap<>();

    public Drive createDrive(String name) {
        if (drivesByName.containsKey(name)) {
            throw new IllegalArgumentException("Drive with name " + name + " already exists");
        }
        Drive drive = new Drive(name);
        drivesByName.put(name, drive);
        return drive;
    }

    public Drive getDrive(String name) {
        return drivesByName.get(name);
    }

    public Folder createFolder(String parentPath, String name) {
        MapChildrenEntity parent = asMapChildrenEntity(resolve(parentPath));
        Folder folder = new Folder(name, parent);
        parent.addChild(folder);
        return folder;
    }

    public TextFile createTextFile(String parentPath, String name, String content) {
        MapChildrenEntity parent = asMapChildrenEntity(resolve(parentPath));
        TextFile tf = new TextFile(name, parent);
        tf.setContent(content);
        parent.addChild(tf);
        return tf;
    }

    public ZipFile createZipFile(String parentPath, String name) {
        MapChildrenEntity parent = asMapChildrenEntity(resolve(parentPath));
        ZipFile zip = new ZipFile(name, parent);
        parent.addChild(zip);
        return zip;
    }

    public void delete(String path) {
        FileSystemEntity entity = resolve(path);
        if (entity instanceof Drive) {
            drivesByName.remove(entity.getName());
        } else {
            FileSystemEntity parent = entity.getParent();
            if (!(parent instanceof MapChildrenEntity)) {
                throw new IllegalStateException("Parent is not a children entity");
            }
            MapChildrenEntity mapChildrenEntity = (MapChildrenEntity) parent;
            mapChildrenEntity.removeChild(entity.getName());
        }
    }

    public void move(String sourcePath, String destFolderPath) {
        FileSystemEntity entity = resolve(sourcePath);
        if (entity instanceof Drive) {
            throw new IllegalArgumentException("Cannot move a drive");
        }
        MapChildrenEntity dest = asMapChildrenEntity(resolve(destFolderPath));
        // Prevent moving a node into its own subtree
        if (isAncestor(entity, dest)) {
            throw new IllegalArgumentException("Cannot move an entity into its own subtree");
        }
        // Remove from current parent
        FileSystemEntity oldParent = entity.getParent();
        if (!(oldParent instanceof MapChildrenEntity)) {
            throw new IllegalStateException("Parent is not a container");
        }
        ((MapChildrenEntity) oldParent).removeChild(entity.getName());
        // Add to new parent (enforces unique name constraint)
        dest.addChild(entity);
    }

    // edge case : check if ancestor is indeed an ancestor of node
    private boolean isAncestor(FileSystemEntity ancestor, FileSystemEntity node) {
        FileSystemEntity current = node;
        while (current != null) {
            if (current == ancestor) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    public void writeToFile(String path, String newContent) {
        FileSystemEntity entity = resolve(path);
        if (!(entity instanceof TextFile)) {
            throw new IllegalArgumentException("Path is not a text file: " + path);
        }
        ((TextFile) entity).setContent(newContent);
    }

    public FileSystemEntity resolve(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path must be non-empty");
        }
        String[] parts = path.split("\\\\");
        if (parts.length == 0) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }
        String driveName = parts[0];
        Drive drive = drivesByName.get(driveName);
        if (drive == null) {
            throw new IllegalArgumentException("Drive not found: " + driveName);
        }
        FileSystemEntity current = drive;
        for (int i = 1; i < parts.length; i++) {
            if (!(current instanceof MapChildrenEntity)) {
                throw new IllegalArgumentException("Entity is not a children: " + current.getPath());
            }
            MapChildrenEntity mapChildrenEntity = (MapChildrenEntity) current;
            FileSystemEntity child = mapChildrenEntity.getChild(parts[i]);
            if (child == null) {
                throw new IllegalArgumentException("Path component not found: " + parts[i]);
            }
            current = child;
        }
        return current;
    }

    private MapChildrenEntity asMapChildrenEntity(FileSystemEntity entity) {
        if (!(entity instanceof MapChildrenEntity)) {
            throw new IllegalArgumentException("Entity is not a container: " + entity.getPath());
        }
        return (MapChildrenEntity) entity;
    }
}
