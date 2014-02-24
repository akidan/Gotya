package database.initial;

import database.original.*;
import database.related.*;
import database.similar.*;
import database.keyword.*;

public abstract class Factory {
	private static Factory factory=null;
	public Factory(){}
	public static Factory getInstance(){
		if(factory==null){
			try{
				Class factoryClass=Class.forName("database.initial.SqlFactory");
				factory=(Factory)factoryClass.newInstance();
			}
			catch(Exception e){
				e.printStackTrace();
				controller.process.Log.printErr(e);
			}
		}
		return factory;
	}
	public abstract Original initOriginal();
	public abstract SqlOriginal initSqlOriginal();
	public abstract Similar initSimilar();
	public abstract SqlSimilar initSqlSimilar();
	public abstract Related initRelated();
	public abstract SqlRelated initSqlRelated();
	public abstract Keyword initKeyword();
	public abstract SqlKeyword initSqlKeyword();
}
