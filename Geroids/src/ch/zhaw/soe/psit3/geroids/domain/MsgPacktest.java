package ch.zhaw.soe.psit3.geroids.domain;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

public class MsgPacktest {
	
	public MessageBufferPacker packer;
	/**
     * Basic usage example
     *
     * @throws IOException
     */
    public ByteBuffer basicUsage() throws IOException {
        // Serialize with MessagePacker.
        // MessageBufferPacker is an optimized version of MessagePacker for packing data into a byte array
        packer = MessagePack.newDefaultBufferPacker();
        packer
        		.packBoolean(false)
                .packBoolean(true)
                .packString("leonardoplusplus")
                .packArrayHeader(8)
                .packInt(999)
                .packInt(999)
                .packInt(999)
                .packInt(999)
                .packInt(999)
                .packInt(999)
                .packInt(555)
        		.packInt(555);
        packer.close(); // Never forget to close (or flush) the buffer
        
		return ByteBuffer.wrap(packer.toByteArray());
        }

}
