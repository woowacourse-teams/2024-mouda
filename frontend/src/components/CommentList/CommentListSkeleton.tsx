import * as S from '@_components/CommentList/ComentList.style';

import CommentCardSkeleton from '@_components/CommentCard/CommentCardSkeleton';
import MessageInput from '@_components/Input/MessagInput/MessageInput';

export default function CommentListSkeleton() {
  return (
    <div css={S.commentListBox()}>
      <CommentCardSkeleton />
      <CommentCardSkeleton />
      <CommentCardSkeleton />
      <CommentCardSkeleton />
      <MessageInput
        placeHolder={'메세지를 입력해주세요'}
        disabled={true}
        onSubmit={(message: string) => {
          message;
        }}
      ></MessageInput>
    </div>
  );
}
