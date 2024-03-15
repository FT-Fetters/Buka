package xyz.ldqc.buka.data.repository.core.action.support;

import com.alibaba.fastjson2.JSONArray;
import xyz.ldqc.buka.data.repository.core.action.Action;

/**
 * 创建Box Action
 * @author Fetters
 */
public class CreateBoxAction implements Action {

    /**
     * Box名称
     */
    private final String name;

    /**
     * Box的格子，包含name及type属性
     */
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
