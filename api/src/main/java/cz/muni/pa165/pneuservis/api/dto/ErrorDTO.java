package cz.muni.pa165.pneuservis.api.dto;

/**
 * @author Martin Spisiak <spisiak@mail.muni.cz> on 16/12/2016.
 */
public class ErrorDTO {

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorDTO)) return false;

        ErrorDTO errorDTO = (ErrorDTO) o;

        if (status != errorDTO.status) return false;
        return message != null ? message.equals(errorDTO.message) : errorDTO.message == null;

    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
