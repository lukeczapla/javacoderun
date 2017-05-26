package frisch.java.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Assignment.class)
public abstract class Assignment_ {

	public static volatile SingularAttribute<Assignment, String> template;
	public static volatile SingularAttribute<Assignment, String> input;
	public static volatile SingularAttribute<Assignment, String> referenceOutput;
	public static volatile SingularAttribute<Assignment, Date> dueDate;
	public static volatile SingularAttribute<Assignment, String> name;
	public static volatile SingularAttribute<Assignment, String> description;
	public static volatile SingularAttribute<Assignment, String> className;
	public static volatile SingularAttribute<Assignment, Long> id;
	public static volatile SingularAttribute<Assignment, Long> type;
	public static volatile SingularAttribute<Assignment, String> initialSource;
	public static volatile SingularAttribute<Assignment, String> sourcefile;

}

