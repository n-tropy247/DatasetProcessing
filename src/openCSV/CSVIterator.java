package openCSV;

import java.io.IOException;
import java.util.Iterator;

public class CSVIterator implements Iterator<String[]> {
    private final CSVReader READER;
    private String[] nextLine;

    public CSVIterator(CSVReader READER) throws IOException {
        this.READER = READER;
        nextLine = READER.readNext();
    }

    @Override
    public boolean hasNext() {
        return nextLine != null;
    }

    @Override
    public String[] next() {
        String[] temp = nextLine;
        try {
            nextLine = READER.readNext();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("This is a read only iterator.");
    }
}