package InMemoryFileSystem;

public class Folder extends MapChildrenEntity {
    public Folder(String name, FileSystemEntity parent) {
        super(name, parent);
    }

    @Override
    public int getSize() {
        return getChildrenSizes();
    }

    @Override
    public EntityType getType() {
        return EntityType.FOLDER;
    }
}
