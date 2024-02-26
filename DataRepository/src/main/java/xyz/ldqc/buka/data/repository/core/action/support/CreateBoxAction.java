package xyz.ldqc.buka.data.repository.core.action.support;

import com.alibaba.fastjson2.JSONArray;
import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * @author Fetters
 */
public class CreateBoxAction implements Action {

    private final String name;

    private final JSONArray lattice;

    public CreateBoxAction(String name, JSONArray lattice) {
        this.name = name;
        this.lattice = lattice;
    }

    public String getName() {
        return name;
    }

    public JSONArray getLattice() {
        return lattice;
    }
}
