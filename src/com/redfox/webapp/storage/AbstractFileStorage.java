package com.redfox.webapp.storage;

import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readeble/writeable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        Resume resume;
        try {
            resume = doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        return resume;
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        File file = null;
        try {
            for (File tmpFile : directory.listFiles()) {
                file = tmpFile;
                resumes.add(doRead(file));
            }
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        return resumes;
    }

    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }

    protected abstract void doWrite(File file, Resume resume) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
