package InMemoryFileSystem;

public class TextFile extends FileSystemEntity {
    private String content = "";

    public TextFile(String name, FileSystemEntity parent) {
        super(name, parent);
    }

    public void setContent(String content) {
        this.content = (content == null) ? "" : content;
    }

    public String getContent() {
        return content;
    }
    
    @Override
    public int getSize() {
        return content.length();
    }

    @Override
    public EntityType getType() {
        return EntityType.TEXT_FILE;
    }
}
