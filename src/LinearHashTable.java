class LinearHashTable<K, V> extends HashTableBase<K, V>
{
	//determi nes whether or not we need to resize
	//to turn off resize, just always return false
    protected boolean needsResize()
    {
    	//linear probing seems to get worse after a load factor of about 70%
		if (_number_of_elements > (0.7 * _primes[_local_prime_index]))
		{
			return true;
		}
		return false;
    }
    
    //called to check to see if we need to resize
    protected void resizeCheck()
    {
    	//Right now, resize when load factor > .7; it might be worth it to experiment with 
		//this value for different kinds of hashtables
		if (needsResize())
		{
			_local_prime_index++;

			HasherBase<K> hasher = _hasher;
			LinearHashTable<K, V> new_hash = new LinearHashTable<K, V>(hasher, _primes[_local_prime_index]);
			
			for (HashItem<K, V> item: _items)
			{
				if (item.isEmpty() == false)
				{
					//add to new hash table
					new_hash.addElement(item.getKey(), item.getValue());
				}
			}
			
			_items = new_hash._items;
		}
    }
    
    public LinearHashTable()
    {
    	super();
    }
    
    public LinearHashTable(HasherBase<K> hasher)
    {
    	super(hasher);
    }
    
    public LinearHashTable(HasherBase<K> hasher, int number_of_elements)
    {
    	super(hasher, number_of_elements);
    }
    
    //copy constructor
    public LinearHashTable(LinearHashTable<K, V> other)
    {
    	super(other);
    }
    
    //concrete implementation for parent's addElement method
    public void addElement(K key, V value)
    {
    	//check for size restrictions
    	resizeCheck();
    	
    	//calculate initial hash based on key
    	int hash = super.getHash(key);

//        System.out.println("Hash for " + key + ": " + hash);
    	
    	//MA #1     TODO: find empty slot to insert (update hash variable as necessary)
        boolean itemInserted = false;
        boolean incrementNumElems = false;
        int offset = 0;
        HashItem<K, V> newItem = new HashItem<K, V>(key, value, false);
        do {
            int currentLoc = hash + offset;

//            System.out.println("Attempting to place key `" + key + "` at position `" + hash + "`");
            // Get current item at hash location
            HashItem<K, V> checkItem = this._items.get(currentLoc);

            // Check if the current key is null; if ti is, put the new item here
            if (checkItem.getKey() == null) {
                this._items.set(currentLoc, newItem);
                incrementNumElems = true;

            // Check if the current item is empty; if it is, put the new item here
            } else if (checkItem.isEmpty()) {
                this._items.set(currentLoc, newItem);
                incrementNumElems = true;

            // Check if the current item has the same actual key as the new item;
            // if it does, then overwrite the item
            } else if (checkItem.getKey().equals(key)) {
                this._items.set(currentLoc, newItem);

            // The item has not been added; increase the hash by one, and try again
            } else {
                offset = hash + offset + 1 < this._items.capacity() ? offset + 1 : -hash;

                // We've gone in a loop; there must not be space (for some reason),
                // so stop trying
                if (hash + offset == hash) {
//                    System.out.println("Insert failed");
                    break;
                }

                continue;
            }
            itemInserted = true;
//            System.out.println("Successful insert");
        } while (!itemInserted);
    	
    	//remember how many things we are presently storing
    	//Hint: do we always increase the size whenever this function is called?
        if (incrementNumElems) {
            _number_of_elements++;
        }
    }
    
    //removes supplied key from hash table
    public void removeElement(K key)
    {
    	//calculate hash
    	int hash = super.getHash(key);
    	
    	//MA #1 TODO: find slot to remove. Remember to check for infinite loop!
    	//ALSO: Use lazy deletion.
        boolean itemRemoved = false;
        boolean decrementNumElems = false;
        int offset = 0;
        do {
            int currentLoc = hash + offset;
            HashItem<K, V> checkItem = this._items.get(currentLoc);
            if (checkItem.getKey() != null && checkItem.getKey().equals(key)) {
                this._items.elementAt(currentLoc).setIsEmpty(true);
            } else {
                offset = hash + offset + 1 < this._items.capacity() ? offset + 1 : -hash;

                // We've gone in a loop; the item must not be here, so stop trying
                if (hash + offset == hash) {
                    break;
                }

                continue;
            }
            itemRemoved = true;
        } while (!itemRemoved);
    	
    	
    	//decrease hashtable size
    	//Hint: do we always reduce the size whenever this function is called?
        if (decrementNumElems) {
            _number_of_elements--;
        }
    }
    
    //returns true if the key is contained in the hash table
    public boolean containsElement(K key)
    {
    	int hash = super.getHash(key);


    	//left incomplete to avoid hints :)
        int offset = 0;
        boolean tableChecked = false;
        do {
            HashItem<K, V> slot = _items.get(hash + offset);
            if (!slot.isEmpty() && slot.getKey().equals(key)) {
                return true;
            } else {
                offset = hash + offset + 1 < _items.capacity() ? offset + 1 : -hash;
                if (hash + offset == hash) {
                    tableChecked = true;
                }
            }
        } while (!tableChecked);

    	return false;
    }
    
    //returns the item pointed to by key
    public V getElement(K key)
    {
    	int hash = super.getHash(key);
        int offset = 0;
        boolean tableChecked = false;
        do {
            HashItem<K, V> slot = _items.get(hash + offset);
            if (!slot.isEmpty() && slot.getKey().equals(key)) {
                return slot.getValue();
            } else {
                offset = hash + offset + 1 < _items.capacity() ? offset + 1 : -hash;
                if (hash + offset == hash) {
                    tableChecked = true;
                }
            }
        } while (!tableChecked);
    	
    	//left incomplete to avoid hints :)
    	return null;
    }
}