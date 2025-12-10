package InMemoryFileSystem;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class MapChildrenEntity extends FileSystemEntity {
    private final Map<String, FileSystemEntity> children = new LinkedHashMap<>();
    
    protected MapChildrenEntity(String name, FileSystemEntity parent) {
        super(name, parent);
    }

    public Collection<FileSystemEntity> getChildren() {
        return Collections.unmodifiableCollection(children.values());
    }

    public FileSystemEntity getChild(String name) {
        return children.get(name);
    }

    public void addChild(FileSystemEntity child) {
        if (child == null) {
            throw new IllegalArgumentException("Child cannot be null");
        }
        String childName = child.getName();
        if (children.containsKey(childName)) {
            throw new IllegalArgumentException("A child with the name '" + childName + "' already exists");
        }
        children.put(child.getName(), child);
        child.setParent(this);
    }

    public void removeChild(String name) {
        FileSystemEntity removedChild = children.remove(name);
        if (removedChild != null) {
            removedChild.setParent(null);
        }
    }

    protected int getChildrenSizes() {
        int sum = 0;
        for (FileSystemEntity child : children.values()) {
            sum += child.getSize();
        }
        return sum;
    }
}
