package com.klashatask.countryapi.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Data
@RequiredArgsConstructor
public class Error {
    private final String fieldName;
    private final String message;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Error) obj;
        return Objects.equals(this.fieldName, that.fieldName) &&
                Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, message);
    }

    @Override
    public String toString() {
        return "Error[" +
                "fieldName=" + fieldName + ", " +
                "message=" + message + ']';
    }
}
