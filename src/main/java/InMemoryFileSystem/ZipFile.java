package InMemoryFileSystem;

public class ZipFile extends MapChildrenEntity {

    public ZipFile(String name, FileSystemEntity parent) {
        super(name, parent);
    }

    @Override
    public int getSize() {
        int sum = getChildrenSizes();
        return sum / 2;
    }

    @Override
    public EntityType getType() {
        return EntityType.ZIP_FILE;
    }
}
