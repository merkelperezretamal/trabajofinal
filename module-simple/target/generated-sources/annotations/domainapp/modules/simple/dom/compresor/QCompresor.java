package domainapp.modules.simple.dom.compresor;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QCompresor extends PersistableExpressionImpl<Compresor> implements PersistableExpression<Compresor>
{
    public static final QCompresor jdoCandidate = candidate("this");

    public static QCompresor candidate(String name)
    {
        return new QCompresor(null, name, 5);
    }

    public static QCompresor candidate()
    {
        return jdoCandidate;
    }

    public static QCompresor parameter(String name)
    {
        return new QCompresor(Compresor.class, name, ExpressionType.PARAMETER);
    }

    public static QCompresor variable(String name)
    {
        return new QCompresor(Compresor.class, name, ExpressionType.VARIABLE);
    }

    public final domainapp.modules.simple.dom.equipo.QEquipo equipo;
    public final StringExpression tag;
    public final StringExpression marca;
    public final StringExpression modelo;
    public final StringExpression frame;
    public final StringExpression cylinder1;
    public final StringExpression cylinder2;
    public final StringExpression cylinder3;
    public final StringExpression cylinder4;

    public QCompresor(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        if (depth > 0)
        {
            this.equipo = new domainapp.modules.simple.dom.equipo.QEquipo(this, "equipo", depth-1);
        }
        else
        {
            this.equipo = null;
        }
        this.tag = new StringExpressionImpl(this, "tag");
        this.marca = new StringExpressionImpl(this, "marca");
        this.modelo = new StringExpressionImpl(this, "modelo");
        this.frame = new StringExpressionImpl(this, "frame");
        this.cylinder1 = new StringExpressionImpl(this, "cylinder1");
        this.cylinder2 = new StringExpressionImpl(this, "cylinder2");
        this.cylinder3 = new StringExpressionImpl(this, "cylinder3");
        this.cylinder4 = new StringExpressionImpl(this, "cylinder4");
    }

    public QCompresor(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.equipo = new domainapp.modules.simple.dom.equipo.QEquipo(this, "equipo", 5);
        this.tag = new StringExpressionImpl(this, "tag");
        this.marca = new StringExpressionImpl(this, "marca");
        this.modelo = new StringExpressionImpl(this, "modelo");
        this.frame = new StringExpressionImpl(this, "frame");
        this.cylinder1 = new StringExpressionImpl(this, "cylinder1");
        this.cylinder2 = new StringExpressionImpl(this, "cylinder2");
        this.cylinder3 = new StringExpressionImpl(this, "cylinder3");
        this.cylinder4 = new StringExpressionImpl(this, "cylinder4");
    }
}
