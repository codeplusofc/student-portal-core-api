package br.com.student.portal.entity.zipcodestack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ZipCodeStackLocationResults {

    private List<ZipCodeStackLocation> codes;

    public List<ZipCodeStackLocation> getCodes() {
        return codes;
    }

    public void setCodes(List<ZipCodeStackLocation> codes) {
        this.codes = codes;
    }
}

