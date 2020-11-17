package cz.muni.fi.pa165.exception;

import org.springframework.dao.DataAccessException;

/**
 * Data access exception for service layer
 *
 * @author Marek Radimersky, 456518
 */
public class ServiceDataAccessException extends DataAccessException {
    public ServiceDataAccessException(String msg) {
        super(msg);
    }

    public ServiceDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
