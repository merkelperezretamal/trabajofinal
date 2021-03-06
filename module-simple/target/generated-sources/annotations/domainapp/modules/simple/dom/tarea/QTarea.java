package domainapp.modules.simple.dom.tarea;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QTarea extends PersistableExpressionImpl<Tarea> implements PersistableExpression<Tarea>
{
    public static final QTarea jdoCandidate = candidate("this");

    public static QTarea candidate(String name)
    {
        return new QTarea(null, name, 5);
    }

    public static QTarea candidate()
    {
        return jdoCandidate;
    }

    public static QTarea parameter(String name)
    {
        return new QTarea(Tarea.class, name, ExpressionType.PARAMETER);
    }

    public static QTarea variable(String name)
    {
        return new QTarea(Tarea.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression nombre;
    public final StringExpression descripcion;
    public final domainapp.modules.simple.dom.mantenimiento.QMantenimiento mantenimiento;

    public QTarea(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.nombre = new StringExpressionImpl(this, "nombre");
        this.descripcion = new StringExpressionImpl(this, "descripcion");
        if (depth > 0)
        {
            this.mantenimiento = new domainapp.modules.simple.dom.mantenimiento.QMantenimiento(this, "mantenimiento", depth-1);
        }
        else
        {
            this.mantenimiento = null;
        }
    }

    public QTarea(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.nombre = new StringExpressionImpl(this, "nombre");
        this.descripcion = new StringExpressionImpl(this, "descripcion");
        this.mantenimiento = new domainapp.modules.simple.dom.mantenimiento.QMantenimiento(this, "mantenimiento", 5);
    }
}
