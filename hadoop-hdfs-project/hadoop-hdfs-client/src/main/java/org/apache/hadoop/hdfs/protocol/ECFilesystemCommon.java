package org.apache.hadoop.hdfs.protocol;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.Options;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.io.IOException;

public class ECFilesystemCommon {
  public ECFilesystemCommon(){
  }

  public static ECFilesystemCommon createECFileSystemCommon(){
    return new ECFilesystemCommon();
  }
  public ErasureCodingPolicy getErasureCodingPolicy(FileStatus status){
    return ((HdfsFileStatus) status).getErasureCodingPolicy();
  }

  public void setErasureCodingPolicy(final FileSystem fs,final Path path,
      final String ecPolicyName) throws IOException {
    DistributedFileSystem dfs = (DistributedFileSystem) fs;
    dfs.setErasureCodingPolicy(path,ecPolicyName);
  }

  public FSDataOutputStream createECOutputStream(FileSystem fs, Path f,
      FsPermission permission, int bufferSize, short replication,
      long blockSize, Options.ChecksumOpt checksumOpt, String ecPolicyName)
      throws IOException {

    DistributedFileSystem dfs = (DistributedFileSystem) fs;
    DistributedFileSystem.HdfsDataOutputStreamBuilder builder =
        dfs.createFile(f).permission(permission).create().overwrite(true)
            .bufferSize(bufferSize).replication(replication)
            .blockSize(blockSize).recursive().ecPolicyName(ecPolicyName);
    if (checksumOpt != null) {
      builder.checksumOpt(checksumOpt);
    }
    return builder.build();
  }
}
