package domainapp.modules.simple.dom.cargadiaria;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QCargaDiaria extends PersistableExpressionImpl<CargaDiaria> implements PersistableExpression<CargaDiaria>
{
    public static final QCargaDiaria jdoCandidate = candidate("this");

    public static QCargaDiaria candidate(String name)
    {
        return new QCargaDiaria(null, name, 5);
    }

    public static QCargaDiaria candidate()
    {
        return jdoCandidate;
    }

    public static QCargaDiaria parameter(String name)
    {
        return new QCargaDiaria(CargaDiaria.class, name, ExpressionType.PARAMETER);
    }

    public static QCargaDiaria variable(String name)
    {
        return new QCargaDiaria(CargaDiaria.class, name, ExpressionType.VARIABLE);
    }

    public final domainapp.modules.simple.dom.equipo.QEquipo equipo;
    public final DateTimeExpression fecha;
    public final NumericExpression<Double> horometro;
    public final NumericExpression<Double> rpm;
    public final NumericExpression<Double> presionAceite;
    public final NumericExpression<Double> temperaturaAceite;
    public final NumericExpression<Double> temperaturaAgua;
    public final NumericExpression<Double> temperaturaSuccion1;
    public final NumericExpression<Double> temperaturaSuccion2;
    public final NumericExpression<Double> temperaturaSuccion3;
    public final NumericExpression<Double> presionSuccion1;
    public final NumericExpression<Double> presionSuccion2;
    public final NumericExpression<Double> presionSuccion3;
    public final NumericExpression<Double> presionDescarga;

    public QCargaDiaria(PersistableExpression parent, String name, int depth)
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
        this.fecha = new DateTimeExpressionImpl(this, "fecha");
        this.horometro = new NumericExpressionImpl<Double>(this, "horometro");
        this.rpm = new NumericExpressionImpl<Double>(this, "rpm");
        this.presionAceite = new NumericExpressionImpl<Double>(this, "presionAceite");
        this.temperaturaAceite = new NumericExpressionImpl<Double>(this, "temperaturaAceite");
        this.temperaturaAgua = new NumericExpressionImpl<Double>(this, "temperaturaAgua");
        this.temperaturaSuccion1 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion1");
        this.temperaturaSuccion2 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion2");
        this.temperaturaSuccion3 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion3");
        this.presionSuccion1 = new NumericExpressionImpl<Double>(this, "presionSuccion1");
        this.presionSuccion2 = new NumericExpressionImpl<Double>(this, "presionSuccion2");
        this.presionSuccion3 = new NumericExpressionImpl<Double>(this, "presionSuccion3");
        this.presionDescarga = new NumericExpressionImpl<Double>(this, "presionDescarga");
    }

    public QCargaDiaria(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.equipo = new domainapp.modules.simple.dom.equipo.QEquipo(this, "equipo", 5);
        this.fecha = new DateTimeExpressionImpl(this, "fecha");
        this.horometro = new NumericExpressionImpl<Double>(this, "horometro");
        this.rpm = new NumericExpressionImpl<Double>(this, "rpm");
        this.presionAceite = new NumericExpressionImpl<Double>(this, "presionAceite");
        this.temperaturaAceite = new NumericExpressionImpl<Double>(this, "temperaturaAceite");
        this.temperaturaAgua = new NumericExpressionImpl<Double>(this, "temperaturaAgua");
        this.temperaturaSuccion1 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion1");
        this.temperaturaSuccion2 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion2");
        this.temperaturaSuccion3 = new NumericExpressionImpl<Double>(this, "temperaturaSuccion3");
        this.presionSuccion1 = new NumericExpressionImpl<Double>(this, "presionSuccion1");
        this.presionSuccion2 = new NumericExpressionImpl<Double>(this, "presionSuccion2");
        this.presionSuccion3 = new NumericExpressionImpl<Double>(this, "presionSuccion3");
        this.presionDescarga = new NumericExpressionImpl<Double>(this, "presionDescarga");
    }
}
