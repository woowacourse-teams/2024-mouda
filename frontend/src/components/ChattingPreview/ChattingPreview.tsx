import {
  container,
  messageContainer,
  peopleContainer,
  smallGrey400,
  tag,
  titleContainer,
  unreadContentContainer,
} from './ChattingPreview.style';

import ChatBubbleSvg from '@_components/Icons/ChatBubbleSvg';
import { ChattingPreview as ChattingPreviewType } from '@_types/index';
import POLICES from '@_constants/poclies';
import UserPreviewList from '@_components/UserPreviewList/UserPreviewList';
import { useMemo } from 'react';
import { useTheme } from '@emotion/react';

interface ChattingPreviewProps {
  chatPreview: ChattingPreviewType;
  onClick: () => void;
}

export default function ChattingPreview(props: ChattingPreviewProps) {
  const { chatPreview, onClick } = props;
  const { title, beforeMoim, lastContent, unreadContentCount, currentPeople } =
    chatPreview;
  const theme = useTheme();
  const imageUrls = useMemo(
    () => new Array(chatPreview.currentPeople + 1).fill(''),
    // TODO:participation.profile 구현되면 아래 코드로 변경
    // () => moim.participants.map((participation) => participation.profile),
    [chatPreview.currentPeople],
  );

  return (
    <div css={container({ beforeMoim, theme })} onClick={onClick}>
      <div css={messageContainer}>
        <div css={titleContainer}>
          <h2 css={theme.typography.s2}>{title}</h2>
          <div css={tag({ theme, beforeMoim })}>
            {beforeMoim ? '모임 전' : '모임 후'}
          </div>
        </div>
        {lastContent && (
          <span css={smallGrey400({ theme })}>{lastContent}</span>
        )}
        {unreadContentCount > 0 && (
          <div css={unreadContentContainer}>
            <ChatBubbleSvg />
            <span css={theme.typography.Tiny}>
              {unreadContentCount > POLICES.maxUnreadMessageCount
                ? POLICES.maxUnreadMessageCount + '+'
                : unreadContentCount}
            </span>
          </div>
        )}
      </div>

      <div css={peopleContainer}>
        <span css={smallGrey400({ theme })}>{`${currentPeople + 1}명`}</span>
        <UserPreviewList imageUrls={imageUrls} />
      </div>
    </div>
  );
}
