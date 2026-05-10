package com.pe.den.pacienteservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileOutputDto {
    private byte[] file;
    private String filename;
    private String formato;
}
