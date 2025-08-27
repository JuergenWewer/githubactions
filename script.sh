for br in $(git for-each-ref --format='%(refname:short)' refs/heads); do
  git push github "$br:$br" || break
done

git ls-remote --heads github

git for-each-ref --format='%(refname:lstrip=3)' refs/remotes/origin \
| grep -v '^HEAD$' \
| while IFS= read -r br; do
  echo "Pushe $br …"
  git push github "refs/remotes/origin/$br:refs/heads/$br" || break
done