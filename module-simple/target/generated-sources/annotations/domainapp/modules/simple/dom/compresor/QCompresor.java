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
    public final NumericExpression<Double> temperaturaSuccion1;
    public final NumericExpression<Double> temperaturaSuccion2;
    public final NumericExpression<Double> temperaturaSuccion3;
    public final NumericExpression<Double> presionSuccion1;
    public final NumericExpression<Double> presionSuccion2;
    public final NumericExpression<Double> presionSuccion3;
    public final NumericExpression<Double> presionDescarga;
    public final NumericExpression<Double> caudalDiario;

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
        this.temperaturaSuccion1 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion1");
        this.temperaturaSuccion2 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion2");
        this.temperaturaSuccion3 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion3");
        this.presionSuccion1 = new NumericExpressionImpl<Double>(this, "presionSuccion1");
        this.presionSuccion2 = new NumericExpressionImpl<Double>(this, "presionSuccion2");
        this.presionSuccion3 = new NumericExpressionImpl<Double>(this, "presionSuccion3");
        this.presionDescarga = new NumericExpressionImpl<Double>(this, "presionDescarga");
        this.caudalDiario = new NumericExpressionImpl<Double>(this, "caudalDiario");
    }

    public QCompresor(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.equipo = new domainapp.modules.simple.dom.equipo.QEquipo(this, "equipo", 5);
        this.tag = new StringExpressionImpl(this, "tag");
        this.temperaturaSuccion1 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion1");
        this.temperaturaSuccion2 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion2");
        this.temperaturaSuccion3 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion3");
        this.presionSuccion1 = new NumericExpressionImpl<Double>(this, "presionSuccion1");
        this.presionSuccion2 = new NumericExpressionImpl<Double>(this, "presionSuccion2");
        this.presionSuccion3 = new NumericExpressionImpl<Double>(this, "presionSuccion3");
        this.presionDescarga = new NumericExpressionImpl<Double>(this, "presionDescarga");
        this.caudalDiario = new NumericExpressionImpl<Double>(this, "caudalDiario");
    }
}
