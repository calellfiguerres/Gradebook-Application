abstract class HasherBase<T>
{
	public abstract int getHash(T item, int mod_by);
}
//
//HasherBase<String> hb = new HasherBase<>();
//All the subclasses of the abstract class, has to implement its defined abstract functions/operations
//
//Define many other Hashers based upon this base: 