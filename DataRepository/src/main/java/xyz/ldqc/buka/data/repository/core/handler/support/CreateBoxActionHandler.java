package xyz.ldqc.buka.data.repository.core.handler.support;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import xyz.ldqc.buka.data.repository.core.action.ActionResult;
import xyz.ldqc.buka.data.repository.core.action.support.CreateBoxAction;
import xyz.ldqc.buka.data.repository.core.engine.buffer.Box;
import xyz.ldqc.buka.data.repository.core.engine.buffer.BoxFactory;
import xyz.ldqc.buka.data.repository.core.engine.structure.DataTypeEnum;
import xyz.ldqc.buka.data.repository.core.handler.ActionHandler;
import xyz.ldqc.buka.data.repository.exception.BoxException;

/**
 * @author Fetters
 */
public class CreateBoxActionHandler implements ActionHandler<CreateBoxAction> {

    @Override
    public Class<CreateBoxAction> getActionClass() {
        return CreateBoxAction.class;
    }

    @Override
    public ActionResult handler(CreateBoxAction action) {
        String name = action.getName();
        JSONArray lattices = action.getLattice();
        BoxFactory boxFactory = BoxFactory.get();

        lattices.forEach(l -> {
            JSONObject lattice = (JSONObject) l;
            String latticeName = lattice.getString("name");
            String type = lattice.getString("type");
            DataTypeEnum dataTypeEnum;
            try {
                dataTypeEnum = DataTypeEnum.valueOf(type);
            } catch (Exception e) {
                throw new BoxException("unknown type (" + type + ")");
            }
            boxFactory.lattice(latticeName, dataTypeEnum);
        });

        Box box = boxFactory.create(name);
        return new ActionResult("ok", box);
    }
}
