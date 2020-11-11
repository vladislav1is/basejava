package com.redfox.webapp.storage;

import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;
import com.redfox.webapp.storage.serializer.SerializerStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializerStrategy serializer;

    protected PathStorage(String dir, SerializerStrategy serializer) {
        Objects.requireNonNull(dir, "dir must not be null");
        Objects.requireNonNull(serializer, "serializer must not be null");
        this.serializer = serializer;
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is mot directory or is mot writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path, resume.getUuid(), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", getFileName(path), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serializer.doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("Path rite error", resume.getUuid(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getFilesStream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesStream().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesStream().count();
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory reed error", e);
        }
    }
}
