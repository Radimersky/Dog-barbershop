package cz.muni.fi.pa165;

import java.io.IOException;

/**
 * Interface for sample data loading facade
 *
 * @author Aneta Moravcikova, 456444
 */
public interface SampleDataLoadingFacade {
    /**
     * Populates the database with sample data
     *
     * @throws IOException
     */
    void loadData() throws IOException;
}

