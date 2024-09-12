import * as S from '@_components/CommentList/CommentCard/CommentCard.style';

import { Comment } from '@_types/index';
import { HTMLProps } from 'react';
import ProfileFrame from '@_components/Profile/ProfileFrame';
import { useTheme } from '@emotion/react';

export interface CommentCardProps extends HTMLProps<HTMLDivElement> {
  comment: Comment;
  onWriteClick?: () => void;
  isChecked?: boolean;
  isChild?: boolean;
}

export default function CommentCard(props: CommentCardProps) {
  const theme = useTheme();
  const {
    comment: { profile, nickname, dateTime, content, children },
    onWriteClick,
    isChecked = false,
    isChild = false,
  } = props;

  return (
    <div css={S.commentContainer()}>
      <div css={S.commentWrapper({ theme, isChecked })}>
        <ProfileFrame
          width={3}
          height={3}
          borderWidth={0}
          src={profile}
        ></ProfileFrame>
        <div css={S.commnetBox()}>
          <div css={S.commnetHeader}>
            <div css={S.commentHeaderLeft}>
              <div css={theme.typography.small}>{nickname}</div>
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
