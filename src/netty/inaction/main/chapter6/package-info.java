/**
 * Contains code examples for chapter 6 of <i>Netty in Action</i>
 *
 * Listing 6.1:     ModifyChannelPipeline.java
 * Listing 6.2:     Writes.java
 * Listing 6.3:     Writes.java
 * Listing 6.4:     Writes.java
 * Listing 6.5:     WriteHandler.java
 * Listing 6.6:     SharableHandler.java        可共享的Handler,OK的用法
 * Listing 6.7:     NotSharableHandler.java     可共享的Handler,不安全的用法
 * Listing 6.x:     一个Channel在它的生命周期内最多只有一个channelActive和channelInactive的状态，
 * 因为一个Channel在其生命周期内只能连接一次，之后就会被回收

 * Listing 6.8:     DiscardHandler.java
 * Listing 6.9:     SimpleDiscardHandler.java
 * Listing 6.10:    DiscardInboundHandler.java
 * Listing 6.11:    DiscardOutboundHandler.java
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
package netty.inaction.main.chapter6;
