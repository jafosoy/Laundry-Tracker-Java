package persistence;

import org.json.JSONObject;

// using basis of persistence package in JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
