import { BetChatRoomDetail, ChatRoomDetail, MoimChatRoomDetail } from '.';

export const isBetChatRoomDetail = (
  detail: ChatRoomDetail,
): detail is BetChatRoomDetail => {
  return (detail as BetChatRoomDetail).attributes.betId !== undefined;
};

export const isMoimChatRoomDetail = (
  detail: ChatRoomDetail,
): detail is MoimChatRoomDetail => {
  return (detail as MoimChatRoomDetail).attributes.moimId !== undefined;
};
