package database.initial;

import database.original.*;
import database.similar.*;
import database.related.*;
import database.keyword.*;

public class SqlFactory extends Factory{
	public Original initOriginal(){
		return (Original) new SqlOriginal();
	}
	public SqlOriginal initSqlOriginal(){
		return new SqlOriginal();
	}
	public Similar initSimilar(){
		return (Similar) new SqlSimilar();
	}
	public SqlSimilar initSqlSimilar(){
		return new SqlSimilar();
	}
	
	public Related initRelated(){
		return (Related) new SqlRelated();
	}
	public SqlRelated initSqlRelated(){
		return new SqlRelated();
	}
	
	public Keyword initKeyword(){
		return (Keyword) new SqlKeyword();
	}
	public SqlKeyword initSqlKeyword(){
		return new SqlKeyword();
	}

}
