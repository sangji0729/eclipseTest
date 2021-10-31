package fantasy;

import java.io.File;

public class FileSystem {
	public void deletingFile(String path, String fileName) {

		System.out.println(path);
		System.out.println(fileName);

		File file = new File(path + fileName);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("Removal complete");
			} else {
				System.out.println("Fail to delete");
			}
		} else {
			System.out.println("File does not Exist!");
		}
	}
}