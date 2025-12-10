package InMemoryFileSystem;

public abstract class FileSystemEntity {
    private String name;
    private FileSystemEntity parent;

    public FileSystemEntity(String name, FileSystemEntity parent) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public FileSystemEntity getParent() {
        return parent;
    }

    public void setParent(FileSystemEntity parent) {
        this.parent = parent;
    }

    public String getPath() {
        if (parent == null) {
           return name;
        }
        return parent.getPath() + "\\" + name;
    }

    public abstract int getSize();

    public abstract EntityType getType();
}
