package lab.cmego.com.cmegoclientandroid.files;

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import lab.cmego.com.cmegoclientandroid.interfaces.CompletionListener;

/**
 * Created by Amit Ishai on 12/28/2016.
 */

public class FileManager {
    private static final String TAG = FileManager.class.getSimpleName();

    public static final String DIRECTORY_CEME = "cMeGo";
    public static final String FILE_BLE_LOG = "ble_log.txt";

    public static File getFile(String relativeFolderPath, String fileName) {
        String path = Environment.getExternalStorageDirectory().toString();

        File folder = new File(path, relativeFolderPath);

        if (!folder.exists()) {
            if (folder.mkdirs()) {
                Log.w(TAG, relativeFolderPath + " Folder created successfully");
            } else {
                Log.w(TAG, "Error creating folder " + relativeFolderPath);
            }
        }

        // For returning path of a folder
        if(TextUtils.isEmpty(fileName)){
            return folder;
        }

        File file = new File(path + File.separator + relativeFolderPath, fileName);

        if (file.exists()) {
            return file;
        } else {
            try {
                if (file.createNewFile()) {
                    Log.w(TAG, "File " + fileName + " created successfully");
                    return file;
                } else {
                    Log.w(TAG, "Error creating File " + fileName);
                }
            } catch (IOException e) {
                Log.w(TAG, "Error creating File " + fileName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        return null;
    }

    //    public static void createFileFromObject(String relativeFolderPath, String fileName, Object fileData) throws IOException {
    //        saveToFile(relativeFolderPath, fileName, new Gson().toJson(fileData), false);
    //    }

    public static void appendToFileAsync(String relativeFolderPath, String fileName, String fileData){
        new SaveFileAsync(relativeFolderPath, fileName, fileData, true).execute();
    }

    private static class SaveFileAsync extends AsyncTask<Void, Void, Void> {

        private String mRelativeFolderPath;
        private String mFileName;
        private String mFileData;
        private boolean mAppendToFile;

        public SaveFileAsync(String relativeFolderPath, String fileName, String fileData, boolean
                appendToFile) {
            mRelativeFolderPath = relativeFolderPath;
            mFileName = fileName;
            mFileData = fileData;
            mAppendToFile = appendToFile;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                saveToFile(mRelativeFolderPath, mFileName, mFileData, mAppendToFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static void saveToFile(String relativeFolderPath, String fileName, String fileData, boolean appendToFile) throws IOException {
        File file = getOrCreateFile(relativeFolderPath, fileName);

        if(file == null){
            return;
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, appendToFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            try {
                outputStreamWriter.append(fileData);
            } catch (IOException e) {
                Log.w(TAG, "Error writing to file");
                throw e;
            }

            try {
                outputStreamWriter.close();
                Log.i(TAG, "outputStreamWriter.close()");
            } catch (IOException e) {
                Log.w(TAG, "outputStreamWriter.close() ERROR: " + e.getMessage());
                throw e;
            }
            try {
                fileOutputStream.close();
                Log.i(TAG, "fileOutputStream.close()");
            } catch (IOException e) {
                Log.w(TAG, "fileOutputStream.close() ERROR: " + e.getMessage());
                throw e;
            }

            Log.i(TAG, "File " + fileName + " saved successfully to " + relativeFolderPath);
        } catch (FileNotFoundException e) {
            Log.w(TAG, "File not found");
            throw e;
        }
    }

    public static File getOrCreateFile(String relativeFolderPath, String fileName) {
        String path = Environment.getExternalStorageDirectory().toString();

        File folder = new File(path, relativeFolderPath);

        if (!folder.exists()) {
            if (folder.mkdirs()) {
                Log.w(TAG, relativeFolderPath + " Folder created successfully");
            } else {
                Log.w(TAG, "Error creating folder " + relativeFolderPath);
            }
        }

        if(folder.exists() && TextUtils.isEmpty(fileName)){
            return folder;
        }

        File file = new File(path + File.separator + relativeFolderPath, fileName);

        if (file.exists()) {
            return file;
        } else {
            try {
                if (file.createNewFile()) {
                    Log.w(TAG, "File " + fileName + " created successfully");
                    return file;
                } else {
                    Log.w(TAG, "Error creating File " + fileName);
                }
            } catch (IOException e) {
                Log.w(TAG, "Error creating File " + fileName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        return null;
    }

    //    public static <T> T getFileContentsAsObject(String relativePath, String fileName, Class<T> classOfT){
    //
    //        String fileAsString = readFromFile(relativePath, fileName);
    //
    //        if(TextUtils.isEmpty(fileAsString)){
    //            return null;
    //        }
    //
    //        try {
    //            return new Gson().fromJson(fileAsString, classOfT);
    //        } catch (JsonSyntaxException e){
    //            return null;
    //        }
    //    }
    public static String readFromFile(String relativePath, String fileName){
        File file = getOrCreateFile(relativePath, fileName);

        if(file == null){
            return null;
        }

        return readFromFile(file.getPath());
    }

    public static String readFromFile(String fullPathAndName){
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(fullPathAndName));

            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //    public static void createFileAsync(String relativeFolderPath, String fileName, InputStream stream, CompletionListener listener){
    //        new SaveByteArrayFileAsync(relativeFolderPath, fileName, stream, listener).execute();
    //    }

    public static void deleteFile(String directory, String fileName) {
        File toDelete = getFileNoCreate(directory, fileName);

        if(toDelete == null){
            return;
        }

        //        toDelete.delete();

        if(toDelete.isDirectory()){
            // In order to delete a directory we need to first delete it's contents
            String folderName = directory + File.separator + toDelete.getName();
            deleteFilesFromDirectory(folderName, getFileNamesFromFolderAsSet(toDelete));
            toDelete.delete();
        } else {
            toDelete.delete();
        }
    }

    private static Set<String> getFileNamesFromFolderAsSet(File directory) {
        ArrayList<String> fileNames = getFileNamesFromFolder(directory);

        HashSet<String> retval = new HashSet<>();

        if(fileNames != null){
            for(String fileName : fileNames){
                retval.add(fileName);
            }
        }

        return retval;
    }

    public static void deleteFilesFromDirectory(String directory, Set<String> filesToDelete) {
        for(String file : filesToDelete){
            deleteFile(directory, file);
        }
    }

    public static void deleteDirectory(String directory) {
        File folder = getFile(directory, null);

        if(folder == null || !folder.isDirectory()){
            return;
        }

        Set<String> filesToDelete = getFileNamesFromFolderAsSet(folder);

        for(String file : filesToDelete){
            deleteFile(directory, file);
        }

        folder.delete();
    }

    private static ArrayList<String> getFileNamesFromFolder(File folder) {
        ArrayList<String> retval = new ArrayList<>();

        String[] filesAndDirectories;

        try {
            filesAndDirectories = folder.list();

            if(filesAndDirectories != null && filesAndDirectories.length > 0){
                for(String name : filesAndDirectories){
                    retval.add(name);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return retval;
    }

    public static void deleteDirectoryIfEmpty(String relativePath) {
        File file = getFile(relativePath, null);

        if(file != null && file.isDirectory() && file.list().length == 0){
            file.delete();
        }
    }

    public static File getFileNoCreate(String relativeFolderPath, String fileName) {
        String path = Environment.getExternalStorageDirectory().toString();

        File file = new File(path + File.separator + relativeFolderPath, fileName);

        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public static void createFile(String relativePath, String fileName, InputStream inputStream) throws IOException {

        File file = getOrCreateFile(relativePath, fileName);

        FileOutputStream fileOutput = new FileOutputStream(file);

        byte[] buffer = new byte[1024];
        int bufferLength = 0;

        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
            fileOutput.write(buffer, 0, bufferLength);
        }

        fileOutput.close();
    }

    // TODO (Amit 5/2/2017) Consolidate with other create file method (String <--> Byte[])
    private static class SaveByteArrayFileAsync extends AsyncTask<Void, Void, Exception> {

        private String mRelativeFolderPath;
        private String mFileName;
        private InputStream mFileData;
        private CompletionListener mListener;

        public SaveByteArrayFileAsync(String relativeFolderPath, String fileName, InputStream fileData, CompletionListener listener) {
            mRelativeFolderPath = relativeFolderPath;
            mFileName = fileName;
            mFileData = fileData;
            mListener = listener;
        }

        @Override
        protected Exception doInBackground(Void... voids) {
            try {
                createFile(mRelativeFolderPath, mFileName, mFileData);
            } catch (IOException e) {
                e.printStackTrace();
                return e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);

            if(mListener == null){
                return;
            }

            if(e == null){
                mListener.onComplete();
            } else {
                mListener.onError(e);
            }
        }
    }

}
