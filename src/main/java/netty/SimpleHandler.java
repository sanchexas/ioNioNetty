package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug(" Клиент подключился ");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug(" Клиент отключился ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.debug("buf: {}", buf);
        StringBuilder sb = new StringBuilder();
        while (buf.isReadable()){
            sb.append((char) buf.readByte());

        }
        log.debug(" Отправленные : {}", sb);
        ctx.fireChannelRead(sb.toString());
    }
}
