import {
  container,
  peopleContainer,
  smallGrey400,
} from './ChattingPreview.style';

import { MoimInfo } from '@_types/index';
import UserPreviewList from '@_components/UserPreviewList/UserPreviewList';
import { useMemo } from 'react';
import { useTheme } from '@emotion/react';

interface ChattingPreviewProps {
  moim: MoimInfo;
  lastChat?: string;
  onClick: () => void;
}

export default function ChattingPreview(props: ChattingPreviewProps) {
  const { lastChat, moim, onClick } = props;
  const theme = useTheme();
  const imageUrls = useMemo(
    () => new Array(moim.currentPeople + 1).fill(''),
    // TODO:participation.profile 구현되면 아래 코드로 변경
    // () => moim.participants.map((participation) => participation.profile),
    [moim],
  );

  return (
    <div css={container} onClick={onClick}>
      <div>
        <h2 css={theme.typography.s2}>{moim.title}</h2>
        {lastChat && <span css={smallGrey400({ theme })}>{lastChat}</span>}
      </div>

      <div css={peopleContainer}>
        <span
          css={smallGrey400({ theme })}
        >{`${moim.currentPeople + 1}명`}</span>
        <UserPreviewList imageUrls={imageUrls} />
      </div>
    </div>
  );
}
