import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName(value = "default")
    float Default;
    final float min;
    final float max;

    public Value(int Default, int min, int max) {
        this.Default = Default;
        this.min = min;
        this.max = max;
    }

    public float getDefault() {
        return Default;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }
}
