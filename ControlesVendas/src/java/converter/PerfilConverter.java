package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import dao.PerfilDao;
import model.PerfilModel;

@FacesConverter(forClass = PerfilModel.class)
public class PerfilConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length()>0) {
            Integer codigo = Integer.valueOf(value);
            PerfilModel perfilModel = new PerfilDao().buscarPorId(codigo);
            return perfilModel;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null){
            PerfilModel model = (PerfilModel)value;
            
            return model.getId()+"";
        }else{
            return null;
        }
    }
    
}
