package InMemoryFileSystem;

public class InMemoryFileSystemMain {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        fs.createDrive("C");

        fs.createFolder("C", "Users");
        fs.createFolder("C\\Users", "Alice");
        fs.createTextFile("C\\Users\\Alice", "notes.txt", "Hello");
        fs.createZipFile("C\\Users\\Alice", "archive.zip");

        System.out.println(fs.resolve("C").getSize());// 5
        System.out.println(fs.resolve("C\\Users\\Alice").getSize());// 5
        System.out.println(fs.resolve("C\\Users\\Alice\\notes.txt").getSize()); // 5

        fs.move("C\\Users\\Alice\\notes.txt", "C\\Users"); // move file

        fs.writeToFile("C\\Users\\notes.txt", "Updated content");
    }
}
