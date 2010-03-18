package com.elpaso.serfj;

import java.io.Serializable;
import java.util.List;

/**
 * @author Eduardo Yáñez
 * Date: 26-abr-2009
 */
public class Resource implements Serializable {
	private static final long serialVersionUID = 6984621304522604750L;
	private String name = null;
    private List<Resource> resources = null;

    public Resource() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
