package InMemoryFileSystem;

public class Drive extends MapChildrenEntity {
    public Drive(String name) {
        super(name, null);
    }

    @Override
    public int getSize() {
        return getChildrenSizes();
    }

    @Override
    public EntityType getType() {
        return EntityType.DRIVE;
    }


}
