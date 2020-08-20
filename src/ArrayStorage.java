/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int size = size();
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        int index = size();
        storage[index] = r;
    }

    Resume get(String uuid) {
        int size = size();
        Resume resume = new Resume();
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                resume.uuid = uuid;
                return resume;
            }
        }
        return resume;
    }

    void delete(String uuid) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < size; j++) {
                    storage[j] = storage[j+1];
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int size = size();
        Resume[] filledStorage = new Resume[size];
        for (int i = 0; i < size; i++) {
            filledStorage[i] = storage[i];
        }
        return filledStorage;
    }

    int size() {
        int size = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break;
            }
            size++;
        }
        return size;
    }
}
