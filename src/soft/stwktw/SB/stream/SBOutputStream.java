package soft.stwktw.SB.stream;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SBOutputStream extends FileOutputStream {

	public SBOutputStream(File file, boolean append)
			throws FileNotFoundException {
		super(file, append);
		// TODO Auto-generated constructor stub
	}

	public SBOutputStream(File file) throws FileNotFoundException {
		super(file);
		// TODO Auto-generated constructor stub
	}

	public SBOutputStream(FileDescriptor fd) {
		super(fd);
		// TODO Auto-generated constructor stub
	}

	public SBOutputStream(String path, boolean append)
			throws FileNotFoundException {
		super(path, append);
		// TODO Auto-generated constructor stub
	}

	public SBOutputStream(String path) throws FileNotFoundException {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void write(byte[] buffer, int byteOffset, int byteCount)
			throws IOException {
		// TODO Auto-generated method stub
		
		super.write(buffer, byteOffset, byteCount);
	}

	@Override
	public void write(int oneByte) throws IOException {
		// TODO Auto-generated method stub
		super.write(oneByte);
	}

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub
		super.flush();
	}

	@Override
	public void write(byte[] buffer) throws IOException {
		// TODO Auto-generated method stub
		super.write(buffer);
	}
	
	

}
