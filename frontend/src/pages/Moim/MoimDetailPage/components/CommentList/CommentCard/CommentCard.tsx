import * as S from './CommentCard.style';

import { Comment } from '@_types/index';
import { HTMLProps } from 'react';
import { useTheme } from '@emotion/react';
import ProfileFrame from '@_components/ProfileFrame/ProfileFrame';
import useNicknameWidthEffect from '@_hooks/useNicknameWidthEffect';

export interface CommentCardProps extends HTMLProps<HTMLDivElement> {
  comment: Comment;
  onWriteClick?: () => void;
  isChecked?: boolean;
  isChild?: boolean;
}

export default function CommentCard(props: CommentCardProps) {
  const {
    comment: { profile, nickname, dateTime, content, children },
    onWriteClick,
    isChecked = false,
    isChild = false,
  } = props;

  const { nicknameRef, formattedNickname } = useNicknameWidthEffect({
    nickname,
    maxNicknameWidth: 100,
  });

  const theme = useTheme();

  return (
    <div css={S.commentContainer()}>
      <div css={S.commentWrapper({ theme, isChecked })}>
        <ProfileFrame width={3} height={3} borderWidth={0} src={profile} />

        <div css={S.commnetBox()}>
          <div css={S.commnetHeader}>
            <div css={S.commentHeaderLeft}>
              <div
                ref={nicknameRef}
                css={[S.commentNickname, theme.typography.small]}
              >
                {formattedNickname}
              </div>
              <div css={S.timestamp({ theme })}>{dateTime}</div>
            </div>
            {!isChild && (
              <div css={S.commentHeaderRight({ theme })}>
                <button onClick={onWriteClick}>답글쓰기</button>
              </div>
            )}
          </div>
          <div css={S.contentBox({ theme })}>{content}</div>
        </div>
      </div>
      {children && (
        <div css={S.commentChildBox()}>
          {children.map((childComment) => (
            <CommentCard
              key={childComment.commentId}
              comment={childComment}
              isChild={true}
            />
          ))}
        </div>
      )}
    </div>
  );
}
