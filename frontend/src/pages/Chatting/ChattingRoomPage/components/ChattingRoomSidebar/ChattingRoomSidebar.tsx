import * as S from './ChattingRoomSidebar.style';

import MemberCard from '@_components/MemberCard/MemberCard';
import { Participation } from '@_types/index';
import { useEffect } from 'react';
import { useTheme } from '@emotion/react';

interface ChattingRoomSidebarProps {
  members: Participation[];
  isOpen?: boolean;
  onClose?: () => void;
}

export default function ChattingRoomSidebar(props: ChattingRoomSidebarProps) {
  const { members, isOpen, onClose } = props;
  const theme = useTheme();

  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = 'hidden';
    }
    if (!isOpen) {
      document.body.style.overflow = '';
    }

    return () => {
      document.body.style.overflow = '';
    };
  }, [isOpen]);

  if (isOpen) {
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
            {members.map(({ darakbangMemberId, nickname, profile }) => {
              return (
                <div css={S.memberWrapper} key={darakbangMemberId}>
                  <MemberCard
                    memberId={darakbangMemberId}
                    name={nickname}
                    imageUrl={profile}
                  />
                </div>
              );
            })}
          </div>
        </aside>
      </div>
    );
  }
  if (!isOpen) {
    return null;
  }
}
