package top.kylewang.bos.web.action.common;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 14:49
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

    /**
     * 抽取模型驱动
     */
    protected T model;
    @Override
    public T getModel() {
        return model;
    }

    public BaseAction(){
        //获取(实现类的)父类的泛型类型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        //获取类型第一个泛型参数
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        //构造model实例
        try {
            model = modelClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("模型构造失败");
        }
    }

    /**
     * 抽取分页查询参数
     */
    protected Integer page;
    protected Integer rows;
    public void setPage(Integer page) {
        this.page = page;
    }
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    /**
     * 抽取分页查询结果压入值栈
     * @param pageData
     */
    public void pushPageDataToValueStack(Page<T> pageData){
        Map<String,Object> result = new HashMap<>(4);
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        ActionContext.getContext().getValueStack().push(result);
    }


}
