package br.com.bg7.appvistoria.projectselection.vo;

import java.io.Serializable;

/**
 * Created by: luciolucio
 * Date: 2017-09-16
 */

public class Location implements Serializable {
    private Long id;
    private String address;

    public Location(Long id, String address) {
        this.id = id;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
}
