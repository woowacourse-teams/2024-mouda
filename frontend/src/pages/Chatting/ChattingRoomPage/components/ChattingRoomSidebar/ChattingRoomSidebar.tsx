import * as S from './ChattingRoomSidebar.style';

import MemberCard from '@_components/MemberCard/MemberCard';
import { Participation } from '@_types/index';
import { useTheme } from '@emotion/react';

interface ChattingRoomSidebarProps {
  members: Participation[];
  isOpen?: boolean;
  onClose?: () => void;
}

export default function ChattingRoomSidebar(props: ChattingRoomSidebarProps) {
  const { members, isOpen, onClose } = props;
  const theme = useTheme();

  if (isOpen) {
    document.body.style.overflow = 'hidden';
    return (
      <div>
        <div css={S.dimmer} onClick={onClose} />
        <aside css={S.sidebar}>
          <header css={S.sidebarHeaderWrapper}>
            <span css={[S.sidebarHeader, theme.typography.s1]}>
              모임 참여 인원
            </span>
          </header>
          <div css={S.members}>
            {members.map(({ nickname, profile }) => {
              return (
                <div css={S.memberWrapper} key={nickname}>
                  <MemberCard name={nickname} imageUrl={profile} />
                </div>
              );
            })}
          </div>
        </aside>
      </div>
    );
  }
  if (!isOpen) {
    document.body.style.overflow = 'auto';
    return null;
  }
}
