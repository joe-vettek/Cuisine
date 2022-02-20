package xueluoanping.cuisine.api.type;

public class BasicBasinType  {

    private final String content;


    public BasicBasinType(String content) {
        this.content = content;

    }

    public BasicBasinType register(Integer integer) {
//		NBTHelper data = NBTHelper.create();
//		data.set
		CuisintAPI.BASIN_TYPES.put(integer, this);
        return this;
    }

	@Override
	public String toString() {
		return content;
	}

}
