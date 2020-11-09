package com.redfox.webapp.storage;

//
//public abstract class AbstractPathStorage extends AbstractStorage<Path> {
//    private Path directory;
//
//    protected AbstractPathStorage(String dir) {
//        directory = Paths.get(dir);
//        Objects.requireNonNull(directory, "directory must not be null");
//        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
//            throw new IllegalArgumentException(dir + "is mot directory or is mot writable")
//        }
//    }
////
////    @Override
////    protected Path getSearchKey(String uuid) {
////        // TODO:
////        return new Path(directory, uuid);
////    }
////
////    @Override
////    protected boolean isExist(Path Path) {
////        // TODO:
////        return Path.exists();
////    }
////
////    @Override
////    protected void doSave(Path Path, Resume resume) {
////        // TODO:
////        try {
////            Path.createNewPath();
////        } catch (IOException e) {
////            throw new StorageException("Couldn't create Path " + Path.getAbsolutePath(), Path.getName(), e);
////        }
////        doUpdate(Path, resume);
////    }
////
////    @Override
////    protected Resume doGet(Path Path) {
////        // TODO:
////        try {
////            return doRead(new BufferedInputStream(new PathInputStream(Path)));
////        } catch (IOException e) {
////            throw new StorageException("Path read error", Path.getName(), e);
////        }
////    }
////
////    @Override
////    protected void doDelete(Path Path) {
////        // TODO:
////        if (!Path.delete()) {
////            throw new StorageException("Path delete error", Path.getName());
////        }
////    }
////
////    @Override
////    protected void doUpdate(Path Path, Resume resume) {
////        // TODO:
////        try {
////            doWrite(new BufferedOutputStream(new PathOutputStream(Path)), resume);
////        } catch (IOException e) {
////            throw new StorageException("Path rite error", resume.getUuid(), e);
////        }
////    }
////
////    @Override
////    protected List<Resume> doCopyAll() {
////        // TODO:
////        Path[] Paths = directory.listPaths();
////        if (Paths == null) {
////            throw new StorageException("Directory reed error", null);
////        }
////        List<Resume> resumes = new ArrayList<>(Paths.length);
////        for (Path Path : Paths) {
////            resumes.add(doGet(Path));
////        }
////        return resumes;
////    }
//
//    @Override
//    public void clear() {
//        try {
//            Files.list(directory).forEach(this::doDelete);
//        } catch (IOException e) {
//            throw new StorageException("Path delete error", null);
//        }
//    }
//
////    @Override
////    public int size() {
////        // TODO:
////        String[] list = directory.list();
////        if (list == null) {
////            throw new StorageException("Directory reed error", null);
////        }
////        return list.length;
////    }
//
//    protected abstract void doWrite(OutputStream os, Resume resume) throws IOException;
//
//    protected abstract Resume doRead(InputStream is) throws IOException;
//}
