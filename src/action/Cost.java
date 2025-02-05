package action;
import board.resource.ResourceType;

public class Cost {
    
    public final int amount;
    public final ResourceType resourceType;

    public Cost(ResourceType resourceType, int amount){
        this.amount = amount;
        this.resourceType = resourceType;
    }
}
