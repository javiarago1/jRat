package Client;

import Client.InformationGathering.System.InfoObject;
import Client.InformationGathering.System.SystemInformation;
import Client.InformationGathering.System.SystemNetworkInformation;
import Client.Tree.DirectoryTree;
import Client.Tree.Tree;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class Client {
    private static final String IP = "192.168.1.133";
    private static final int PORT = 3055;

    public static void main(String[] args) {


        while (true) {
            try {
                System.out.println("Conectando...");

                Socket s = new Socket(IP, PORT);

                System.out.println("Conectado");

                ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(s.getInputStream());
                DataOutputStream dataOutput = new DataOutputStream(s.getOutputStream());
                DataInputStream dataInput = new DataInputStream(s.getInputStream());

                Object reader;
                do {
                    reader = input.readObject();
                    if (reader instanceof Object[]) {
                        output.writeObject(new Object[]{new SystemNetworkInformation(), new SystemInformation()});
                    } else if (reader instanceof String e && e.equals("DISKS")) {
                        output.writeObject(File.listRoots());
                    } else if (reader instanceof String e && e.equals("SYS_DETAILS")) {
                        output.writeObject(new Object[]{new SystemNetworkInformation(), new SystemInformation()});
                    } else if (reader instanceof InfoObject e) {
                        switch (e.getCommand()) {
                            case "TREE" -> {
                                System.out.println("Path recibido " + e.getPath());
                                output.writeObject(new Tree(e.getPath()).getTree());
                            }
                            case "TREE_DIRECTORIES" -> {
                                System.out.println("Path recibido " + e.getPath());
                                output.writeObject(new DirectoryTree(e.getPath()).getTree());
                            }
                            case "DOWNLOAD" -> {
                                List<File> fileArray = new ArrayList<>(e.getFilesArray());
                                System.out.println(fileArray);
                                ZipFile zipFile = new ZipFile("fichero.zip");
                                for (File a : fileArray) {
                                    if (a.isDirectory()) {
                                        zipFile.addFolder(a);
                                    } else {
                                        zipFile.addFile(a);
                                    }
                                }
                                File zipToSend = zipFile.getFile();
                                FileInputStream fileInputStream = new FileInputStream(zipToSend);
                                byte[] fileContentBytes = new byte[(int) zipToSend.length()];
                                int length = fileInputStream.read(fileContentBytes);
                                fileInputStream.close();
                                dataOutput.writeInt(length);
                                dataOutput.write(fileContentBytes);
                                FileUtils.deleteQuietly(zipToSend);
                            }
                            case "UPLOAD" -> {
                                List<File> directories = new ArrayList<>(e.getFilesArray());
                                int length = 0;
                                try {
                                    length = dataInput.readInt();
                                } catch (IOException er) {
                                    er.printStackTrace();
                                }

                                byte[] byteContent;
                                if (length > 0) {
                                    byteContent = new byte[length];
                                    try (FileOutputStream fileToCreate = new FileOutputStream("temp.zip")) {
                                        dataInput.readFully(byteContent, 0, length);
                                        fileToCreate.write(byteContent);
                                        ZipFile tempZip = new ZipFile("temp.zip");
                                        for (File a : directories) {
                                            tempZip.extractAll(a.toString());
                                        }
                                        FileUtils.deleteQuietly(tempZip.getFile());
                                    } catch (IOException er) {
                                        er.printStackTrace();

                                    }
                                }
                            }
                            case "COPY" -> {
                                List<File> fileArray = new ArrayList<>(e.getFilesArray());
                                List<File> directories = new ArrayList<>(e.getDirectories());
                                for (File a : fileArray) {
                                    for (File i : directories) {
                                        if (a.isFile()) {
                                            FileUtils.copyFileToDirectory(a, i);
                                        } else {
                                            FileUtils.copyDirectoryToDirectory(a, i);
                                        }
                                    }
                                }
                                System.out.println("Archivos copiados");
                            }
                            case "MOVE" -> {
                                List<File> filesArray = new ArrayList<>(e.getFilesArray());
                                System.out.println("Archivos a mover -> " + filesArray);
                                File destination = e.getDestination();
                                System.out.println("Destino -> " + destination);
                                for (File a : filesArray) {
                                    if (a.isFile()) {
                                        FileUtils.moveFileToDirectory(a, destination, true);
                                    } else {
                                        FileUtils.moveDirectoryToDirectory(a, destination, true);
                                    }
                                }
                                System.out.println("Archivos movidos");
                            }
                        }
                    }


                } while (reader != null);
            } catch (ConnectException e) {
                System.out.println("Connection refused");
            } catch (SocketException ignored) {
                System.out.println("Connection reset");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

}