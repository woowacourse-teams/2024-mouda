import {
  chatLine,
  chatMessageStyle,
  messageContainer,
  senderStyle,
  timeStyle,
} from './Chatstyle';

import { Chat as ChatType } from '@_types/index';
import { PropsWithChildren } from 'react';
import { formatHhmmToKoreanWithPrefix } from '@_utils/formatters';
import { useTheme } from '@emotion/react';
import ChatProfileImage from '../ChatProfileImage/ChatProfileImage';

export interface ChatMessageProps extends PropsWithChildren {
  chat: ChatType;
}

export default function Chat(props: ChatMessageProps) {
  const { chat, children } = props;
  const { participation, time, isMyMessage } = chat;

  const theme = useTheme();

  return (
    <div css={chatMessageStyle({ isMyMessage })}>
      {/* TODO: 추후 프로필 사진 추가시 변경해야함  */}
      <ChatProfileImage
        imageUrl={participation.profile}
        memberId={chat.participation.darakbangMemberId}
      />
      <div css={messageContainer({ isMyMessage })}>
        <span css={senderStyle({ theme })}>{participation.nickname}</span>
        <div css={chatLine({ isMyMessage })}>
          {children}
          <span css={timeStyle({ theme })}>
            {formatHhmmToKoreanWithPrefix(time)}
          </span>
        </div>
      </div>
    </div>
  );
}
